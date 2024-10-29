const { Client } = require('@stomp/stompjs');
const WebSocket = require('ws'); // Necessário para Node.js

// Configura o WebSocket para a biblioteca STOMP no Node.js
const client = new Client({
    brokerURL: 'ws://localhost:8080/ws/websocket', // Atualize com o URL do seu WebSocket
    connectHeaders: {
        login: 'user', // Atualize se necessário
        passcode: 'password' // Atualize se necessário
    },
    webSocketFactory: () => new WebSocket('ws://localhost:8080/ws/websocket'),
    onConnect: () => {
        console.log('Conectado ao WebSocket STOMP');

        // Inscreve-se para ouvir mensagens no tópico '/topic/public'
        client.subscribe('/topic/public', message => {
            console.log('Mensagem recebida:', message.body);
        });

        // Envia uma mensagem para o destino '/app/chat.sendMessage'
        client.publish({
            destination: '/app/chat.sendMessage',
            body: JSON.stringify({ content: 'Olá do cliente JavaScript!' })
        });
    },
    onStompError: frame => {
        console.error('Erro STOMP:', frame.headers['message']);
        console.error('Detalhes:', frame.body);
    }
});

client.activate();