package com.netease.rpc.rpc;

public class Content {
    String uri;
    String content;

    public Content() {
    }

    public Content(String uri, String content) {
        this.uri = uri;
        this.content = content;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
