package ru.indieplay.ariamis.client;

public class Proxy extends ru.indieplay.ariamis.common.Proxy {
    SwingHandler sw;

    KeyHandler a ;
    TickHandler b;

    public void init(){
    sw = new SwingHandler();
        a = new KeyHandler();
        b = new TickHandler();
    }

}
