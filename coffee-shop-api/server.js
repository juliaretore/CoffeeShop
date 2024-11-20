const express = require('express');
const mongoose = require('mongoose');
require('dotenv').config(); // Carregar as variáveis do arquivo .env
console.log('MONGO_URL:', process.env.MONGO_URL);

const app = express();
const port = process.env.PORT || 8000; // Porta definida no ambiente ou padrão 8000

// Conectar ao MongoDB usando a URL do .env
mongoose.connect(process.env.MONGO_URL)
    .then(() => console.log('Conectado ao MongoDB'))
    .catch(err => console.error('Erro ao conectar ao MongoDB:', err));

// Modelo de Produto
const produtoSchema = new mongoose.Schema({
    id: Number,
    name: String,
    description: String,
    price: Number,
    image: String,
    category: Number,
});
const Produto = mongoose.model('Produto', produtoSchema);

// Rota para buscar produtos
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

// Iniciar o servidor
app.listen(port, () => {
    console.log(`Servidor rodando na porta ${port}`);
});
