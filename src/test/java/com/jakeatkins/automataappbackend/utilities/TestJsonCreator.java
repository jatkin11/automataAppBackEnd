package com.jakeatkins.automataappbackend.utilities;

public class TestJsonCreator {
    
    public static String jsonOfABC(){
        
        return """ 
        {"nodes":[
            {"id":"0",
            "position":{"x":0,"y":0},
            "data":{
                "label":"q0",
                "startingState":true,
                "acceptingState":false},
                "type":"custom"
            },
            {"id":"1",
            "position":{"x":120,"y":0},
            "data":{
                "label":"q1",
                "startingState":false,
                "acceptingState":false},
            "type": "custom"
            },
            {"id":"2",
            "position":{"x":240,"y":0},
            "data":{
                "label":"q2",
                "startingState":false,
                "acceptingState":false},
                "type":"custom"
            },
            {"id":"3",
            "position":{"x":360,"y":0},
            "data":{
                "label":"q3",
                "startingState":false,
                "acceptingState":true},
                "type":"custom"
            }],
            "edges":[
                {"id":"2-c->3",
                "source":"2",
                "target":"3",
                "label":"c",
                "type": "default"},
                {"id":"1-b->2",
                "source":"1",
                "target":"2",
                "label":"b",
                "type":"default"},
                {"id":"0-a->1",
                "source":"0",
                "target":"1",
                "label":"a",
                "type":"default"}
                ]} 
            """;
        }   
}