const express = require('express');
const mongoose = require('mongoose');
const bcrypt = require('bcryptjs');
require('dotenv').config(); 
console.log('MONGO_URL:', process.env.MONGO_URL);

const app = express();
const port = process.env.PORT || 8000;

// middleware para parsear o corpo das requisicoes JSON
app.use(express.json());

// conectar ao MongoDB usando a URL do .env
mongoose.connect(process.env.MONGO_URL)
    .then(() => console.log('Conectado ao MongoDB'))
    .catch(err => console.error('Erro ao conectar ao MongoDB:', err));

// modelo produto
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

// modelo user
const userSchema = new mongoose.Schema({
    username: { type: String, required: true, unique: true },
    email: { type: String, required: true, unique: true },
    password: { type: String, required: true },
    address: String,
    phone: String
});

// middleware para hash de senha
userSchema.pre('save', async function(next) {
    if (this.isModified('password') || this.isNew) {
        this.password = await bcrypt.hash(this.password, 10);
    }
    next();
});

const User = mongoose.model('User', userSchema);

// registrar
app.post('/api/register', async (req, res) => {
    try {
        const { username, email, password, address, phone } = req.body;
        const newUser = new User({ username, email, password, address, phone });
        await newUser.save();

        // Resposta de sucesso
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
        console.log("Recebendo requisição de login:", req.body); // Adicionar log
        const { username, password } = req.body;

        const user = await User.findOne({ username });
        if (!user) {
            console.log("Usuário não encontrado:", username); // Log de usuário não encontrado
            return res.status(404).send('Usuário não encontrado');
        }

        const isMatch = await bcrypt.compare(password, user.password);
        if (isMatch) {
            console.log("Login bem-sucedido:", username); // Log de sucesso
            res.send({
                success: true,
                message: 'Login bem-sucedido',
                user: {
                    username: user.username,
                    email: user.email,
                    address: user.address,
                    phone: user.phone
                }
            });
        } else {
            console.log("Senha incorreta para o usuário:", username); // Log de senha incorreta
            res.status(401).send({ success: false, message: 'Senha incorreta' });
        }
    } catch (error) {
        console.error("Erro no login:", error.message); // Log de erro
        res.status(500).send({ success: false, message: error.message });
    }
});


// Buscar todos os usuários (sem expor senhas)
app.get('/api/users', async (req, res) => {
    try {
        // Buscar todos os usuários, mas sem retornar o campo `password`
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
