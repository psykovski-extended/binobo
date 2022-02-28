function disconnect(node){
    node.onclick = () => connect(node);
    node.classList.remove("connected");
    data_buffer = [];

    socket.send('close session');
    socket.close();

    document.getElementById("connect").innerHTML = "connect";
}

function connect(node) {
    node.onclick = () => disconnect(node);
    node.classList.add("connected");

    socket = new WebSocket('wss://emulator.binobo.io/' + token); // change to ws://emulator.binobo.io
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
