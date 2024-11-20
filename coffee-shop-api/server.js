const express = require('express');
const app = express();
const port = 8000;  // Porta local do servidor

// Dados fictícios para exemplificar a resposta da API
const produtos = [
    { id: 1, name: 'Café Espresso', category: 0, price: 15 },
    { id: 2, name: 'Café Latte', category: 0, price: 18 },
    { id: 3, name: 'Coxinha', category: 1, price: 5 },
    { id: 4, name: 'Bolo de Chocolate', category: 2, price: 8 }
];

// Rota da API
app.get('/api/produtos', (req, res) => {
    const category = req.query.category;
    const filteredProducts = produtos.filter(prod => prod.category == category);
    res.json(filteredProducts);  // Retorna os produtos filtrados no formato JSON
});

// Inicia o servidor na porta 8000
app.listen(port, () => {
    console.log(`Servidor rodando em http://localhost:${port}`);
});

app.get('/api/produtos', (req, res) => {
    const category = req.query.category;
    console.log(`Requisição recebida para categoria: ${category}`);
    const filteredProducts = produtos.filter(prod => prod.category == category);
    console.log(`Produtos retornados: ${JSON.stringify(filteredProducts)}`);
    res.json(filteredProducts);
});
