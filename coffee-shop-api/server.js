const express = require('express');
const mongoose = require('mongoose');
const bcrypt = require('bcryptjs');
require('dotenv').config();
console.log('MONGO_URL:', process.env.MONGO_URL);

const app = express();
const port = process.env.PORT || 8000;

// middleware para parsear o corpo das requisicoes JSON
app.use(express.json());

// conectar MongoDB usando a URL do .env
mongoose.connect(process.env.MONGO_URL)
    .then(() => console.log('Conectado ao MongoDB'))
    .catch(err => console.error('Erro ao conectar ao MongoDB:', err));

// produto
const produtoSchema = new mongoose.Schema({
    id: Number,
    name: String,
    description: String,
    price: Number,
    image: String,
    category: Number,
});

const Produto = mongoose.model('Produto', produtoSchema);

// buscar produtos
app.get('/api/produtos', async (req, res) => {
    const category = req.query.category;
    try {
        const produtos = category
            ? await Produto.find({ category: category }) // Filtrar por categoria se fornecida
            : await Produto.find(); // Retornar todos os produtos
        res.json(produtos);
    } catch (err) {
        res.status(500).send('Erro ao buscar produtos');
    }
});

// carrinho
const CartItemSchema = new mongoose.Schema({
    product: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Product',
        required: true,
    },
    quantity: { type: Number, required: true },
});

// pedidos
const OrderSchema = new mongoose.Schema({
    items: [CartItemSchema],
    totalPrice: { type: Number, required: true },
    timestamp: { type: Date, default: Date.now },
});

// user
const userSchema = new mongoose.Schema({
    name: { type: String, required: true }, 
    username: { type: String, required: true, unique: true }, 
    email: { type: String, required: true, unique: true },
    password: { type: String, required: true },
    address: String,
    phone: String, 
    orders: [OrderSchema],
});

// hash de senha
userSchema.pre('save', async function (next) {
    if (this.isModified('password') || this.isNew) {
        this.password = await bcrypt.hash(this.password, 10);
    }
    next();
});

const User = mongoose.model('User', userSchema);

// registrar
app.post('/api/register', async (req, res) => {
    try {
        const { name, username, email, password, address, phone } = req.body;
        const newUser = new User({ name, username, email, password, address, phone });
        await newUser.save();

        res.status(201).send({
            success: true,
            message: 'Usuário registrado com sucesso'
        });
    } catch (error) {
        console.error('Erro ao registrar usuário:', error.message);
        res.status(400).send({
            success: false,
            message: error.message
        });
    }
});

// login
app.post('/api/login', async (req, res) => {
    try {
        console.log("Recebendo requisição de login:", req.body);
        const { username, password } = req.body;

        const user = await User.findOne({ username });
        if (!user) {
            console.log("Usuário não encontrado:", username);
            return res.status(404).send({ success: false, message: 'Usuário não encontrado' });
        }

        const isMatch = await bcrypt.compare(password, user.password);
        if (isMatch) {
            const response = {
                success: true,
                message: 'Login bem-sucedido',
                user: {
                    id: user._id.toString(), // Aqui convertendo para string
                    name: user.name,
                    username: user.username,
                    email: user.email,
                    address: user.address,
                    phone: user.phone
                }
            };
            console.log("Resposta enviada para o cliente:", response); // Adicione este log
            res.send(response);
        } else {
            console.log("Senha incorreta para o usuário:", username);
            res.status(401).send({ success: false, message: 'Senha incorreta' });
        }
    } catch (error) {
        console.error("Erro no login:", error.message);
        res.status(500).send({ success: false, message: error.message });
    }
});


// fazer pedido
app.post('/api/orders', async (req, res) => {
    const { userId, items, totalPrice } = req.body;

    try {
        const user = await User.findById(userId);
        if (!user) {
            return res.status(404).send({ message: 'Usuário não encontrado' });
        }

        // Mapear os itens para incluir o campo `product` como ObjectId
        const processedItems = items.map(item => ({
            product: new mongoose.Types.ObjectId(item.productId), // Converte productId para ObjectId
            quantity: item.quantity
        }));

        const newOrder = {
            items: processedItems,
            totalPrice,
            timestamp: new Date(),
        };

        user.orders.push(newOrder);
        await user.save();

        res.status(201).send({ message: 'Pedido criado com sucesso', order: newOrder });
    } catch (error) {
        console.error('Erro ao criar pedido:', error);
        res.status(500).send({ message: 'Erro ao criar pedido', error });
    }
});



// buscar pelo username
app.get('/api/users/:username', async (req, res) => {
    const { username } = req.params;

    try {
        const user = await User.findOne({ username }).select('-password'); // Excluir a senha
        if (!user) {
            return res.status(404).send({ message: 'Usuário não encontrado' });
        }

        res.status(200).send(user);
    } catch (error) {
        res.status(500).send({ message: 'Erro ao buscar usuário', error });
    }
});

// buscar todos os usuarios
app.get('/api/users', async (req, res) => {
    try {
        const users = await User.find({}, { password: 0 }); // `{ password: 0 }` exclui o campo password
        res.json(users);
    } catch (error) {
        res.status(500).json({ error: 'Erro ao buscar usuários' });
    }
});

// iniciar servidor
app.listen(port, () => {
    console.log(`Servidor rodando na porta ${port}`);
});
