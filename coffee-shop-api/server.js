const express = require('express');
const app = express();
const port = 8000;  // Porta local do servidor

// Dados fictícios para exemplificar a resposta da API
const produtos = [
    { id: 1, name: 'Café Espresso', description:'a', price: 15, image: 'https://www.pokemon.com/static-assets/content-assets/cms2/img/pokedex/full/001.png', category: 0 },
    { id: 2, name: 'Café Latte',  description: 'b', price: 18,image: 'https://www.pokemon.com/static-assets/content-assets/cms2/img/pokedex/full/001.png', category: 0},
    { id: 5, name: 'Café Latte',  description: 'b', price: 18,image: 'https://www.pokemon.com/static-assets/content-assets/cms2/img/pokedex/full/001.png', category: 0},
    { id: 6, name: 'Café Latte',  description: 'b', price: 18,image: 'https://www.pokemon.com/static-assets/content-assets/cms2/img/pokedex/full/001.png', category: 0},
    { id: 7, name: 'Café Latte',  description: 'b', price: 18,image: 'https://www.pokemon.com/static-assets/content-assets/cms2/img/pokedex/full/001.png', category: 0},
    { id: 3, name: 'Coxinha', description: 'c', price: 5, image: 'https://www.pokemon.com/static-assets/content-assets/cms2/img/pokedex/full/002.png', category: 1},
    { id: 4, name: 'Bolo de Chocolate', description:'d', price: 8, image: 'https://www.pokemon.com/static-assets/content-assets/cms2/img/pokedex/full/003.png', category: 2}
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
