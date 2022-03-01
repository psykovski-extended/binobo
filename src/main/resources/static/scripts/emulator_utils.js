function disconnect(node){
    node.onclick = () => connect(node);
    node.classList.remove("connected");
    data_buffer = [];

    socket.send('close session');
    socket.close();

    clearInterval(socket_ping_interval);

    socket_ping.close();
    socket_ping_receiver.close();

    document.getElementById("connect").innerHTML = "connect";
}

function connect(node) {
    node.onclick = () => disconnect(node);
    node.classList.add("connected");

    socket = new WebSocket('wss://emulator.binobo.io/' + token);
    socket_ping = new WebSocket('wss://emulator.binobo.io/');
    socket_ping_receiver = new WebSocket('wss://emulator.binobo.io/ping_' + token);

    socket_ping_receiver.addEventListener('message', ({ data }) => {
        let x = new Date();
        let ping = x.getTime() - eval(data);
        console.log(ping);
        let node = document.getElementById("ping")
        node.innerHTML = ping + "ms"
    });

    socket_ping.addEventListener('open', evt => {
        socket_ping_interval = setInterval(() => {
            socket_ping.send('[ping_' + token + "," + (new Date()).getTime() + "]");
        }, 1000);
    });

    socket.addEventListener('message', ({data}) => {
        try {
            for(i of eval(data)){
                data_buffer[data_buffer.length] = i;
            }
        } catch (e) {
            console.log('wrong data-format received: ' + data)
        }
    });
    socket.addEventListener('open', evt => {
        socket.send('receiver for: ' + token)
    })
    document.getElementById("connect").innerHTML = "stop";
}

function sync() {
    data_buffer = [];
}

function copy_token(){
    let node = document.querySelector("#token_input");
    node.select();
    document.execCommand("copy");
}

function apply_filter_to_data(data_to_filter){
    let d = 0.6;
    let d_m1 = 1 - d;
}
