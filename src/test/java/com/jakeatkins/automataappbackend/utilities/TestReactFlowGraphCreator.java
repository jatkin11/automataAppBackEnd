package com.jakeatkins.automataappbackend.utilities;

import java.util.ArrayList;
import java.util.List;

import com.jakeatkins.automataappbackend.dto.NodeData;
import com.jakeatkins.automataappbackend.dto.Position;
import com.jakeatkins.automataappbackend.dto.ReactFlowEdge;
import com.jakeatkins.automataappbackend.dto.ReactFlowGraph;
import com.jakeatkins.automataappbackend.dto.ReactFlowNode;

public class TestReactFlowGraphCreator {
    
    public static ReactFlowGraph reactFlowGraphValidGraph(){
        List<ReactFlowEdge> edges = new ArrayList<>();
        List<ReactFlowNode> nodes = new ArrayList<>();

        ReactFlowNode q0 = new ReactFlowNode("0", 
        new Position(0,0), 
        new NodeData("q0",
        true,
        false),
        "custom");

        ReactFlowNode q1 = new ReactFlowNode("1", 
        new Position(100,100), 
        new NodeData("q1",
        false,
        true),
        "custom");

        ReactFlowEdge q0Toq1 = new ReactFlowEdge("0-a->1",
        "0",
        "1",
        "a",
        "default"
        );

        nodes.add(q0);
        nodes.add(q1);
        edges.add(q0Toq1);

        return new ReactFlowGraph("NFA", edges, nodes);
    }


    public static ReactFlowGraph reactFlowGraphWithOneNodeGraph(){
        List<ReactFlowEdge> edges = new ArrayList<>();
        List<ReactFlowNode> nodes = new ArrayList<>();

        ReactFlowNode q0 = new ReactFlowNode("0", 
        new Position(100,100), 
        new NodeData("q0",
        true,
        false),
        "custom");

        nodes.add(q0);

        return new ReactFlowGraph("NFA", edges, nodes);
    }


    public static ReactFlowGraph reactFlowGraphWithNullGraph(){
        return null;
    }


    public static ReactFlowGraph reactFlowGraphWithNullNodes(){
        List<ReactFlowEdge> edges = new ArrayList<>();

        ReactFlowEdge q0Toq1 = new ReactFlowEdge("e0",
        "0",
        "1",
        "a",
        "default"
        );

        edges.add(q0Toq1);

        return new ReactFlowGraph("NFA", edges, null);
    }


    public static ReactFlowGraph reactFlowGraphWithNullEdges(){
        List<ReactFlowNode> nodes = new ArrayList<>();
        
        ReactFlowNode q0 = new ReactFlowNode("0", 
        new Position(100,100), 
        new NodeData("q0",
        true,
        false),
        "custom");

        ReactFlowNode q1 = new ReactFlowNode("1", 
        new Position(100,100), 
        new NodeData("q1",
        false,
        true),
        "custom");

        nodes.add(q0);
        nodes.add(q1);

        return new ReactFlowGraph("NFA", null, nodes);
    }


    public static ReactFlowGraph reactFlowGraphWithEmptyNodesList(){
        List<ReactFlowEdge> edges = new ArrayList<>();
        List<ReactFlowNode> nodes = new ArrayList<>();

        ReactFlowEdge q0Toq1 = new ReactFlowEdge("e0",
        "0",
        "1",
        "a",
        "default"
        );

        edges.add(q0Toq1);

        return new ReactFlowGraph("NFA", edges, nodes);
    }


    public static ReactFlowGraph reactFlowGraphWithANullNode(){
        List<ReactFlowEdge> edges = new ArrayList<>();
        List<ReactFlowNode> nodes = new ArrayList<>();

        ReactFlowNode q0 = new ReactFlowNode("0", 
        new Position(100,100), 
        new NodeData("q0",
        true,
        false),
        "custom");

        ReactFlowNode q1 = null;

        ReactFlowEdge q0Toq1 = new ReactFlowEdge("e0",
        "0",
        "1",
        "a",
        "default"
        );

        nodes.add(q0);
        nodes.add(q1);
        edges.add(q0Toq1);
        return new ReactFlowGraph("NFA", edges, nodes);
    }


    public static ReactFlowGraph reactFlowGraphWithANodeWithANullId(){
        List<ReactFlowEdge> edges = new ArrayList<>();
        List<ReactFlowNode> nodes = new ArrayList<>();

        ReactFlowNode q0 = new ReactFlowNode(null, 
        new Position(100,100), 
        new NodeData("q0",
        true,
        false),
        "custom");

        ReactFlowNode q1 = new ReactFlowNode("1", 
        new Position(100,100), 
        new NodeData("q1",
        false,
        true),
        "custom");

        ReactFlowEdge q0Toq1 = new ReactFlowEdge("e0",
        "0",
        "1",
        "a",
        "default"
        );

        nodes.add(q0);
        nodes.add(q1);
        edges.add(q0Toq1);

        return new ReactFlowGraph("NFA", edges, nodes);
    }


    public static ReactFlowGraph reactFlowGraphWithADupeNode(){
        List<ReactFlowEdge> edges = new ArrayList<>();
        List<ReactFlowNode> nodes = new ArrayList<>();

        ReactFlowNode q0 = new ReactFlowNode("0", 
        new Position(100,100), 
        new NodeData("q0",
        true,
        false),
        "custom");

        ReactFlowNode q1 = new ReactFlowNode("1", 
        new Position(100,100), 
        new NodeData("q1",
        false,
        true),
        "custom");

        ReactFlowEdge q0Toq1 = new ReactFlowEdge("e0",
        "0",
        "1",
        "a",
        "default"
        );

        nodes.add(q0);
        nodes.add(q0);
        nodes.add(q1);
        edges.add(q0Toq1);

        return new ReactFlowGraph("NFA", edges, nodes);
    }


    public static ReactFlowGraph reactFlowGraphWithANodeWithInvalidId(){
        List<ReactFlowEdge> edges = new ArrayList<>();
        List<ReactFlowNode> nodes = new ArrayList<>();

        ReactFlowNode q0 = new ReactFlowNode("XX", 
        new Position(100,100), 
        new NodeData("q0",
        true,
        false),
        "custom");

        ReactFlowNode q1 = new ReactFlowNode("1", 
        new Position(100,100), 
        new NodeData("q1",
        false,
        true),
        "custom");

        ReactFlowEdge q0Toq1 = new ReactFlowEdge("e0",
        "0",
        "1",
        "a",
        "default"
        );

        nodes.add(q0);
        nodes.add(q1);
        edges.add(q0Toq1);

        return new ReactFlowGraph("NFA", edges, nodes);
    }

    public static ReactFlowGraph reactFlowGraphWithANodeWithNullLabel(){
        List<ReactFlowEdge> edges = new ArrayList<>();
        List<ReactFlowNode> nodes = new ArrayList<>();

        ReactFlowNode q0 = new ReactFlowNode("0", 
        new Position(100,100), 
        new NodeData(null,
        true,
        false),
        "custom");

        ReactFlowNode q1 = new ReactFlowNode("1", 
        new Position(100,100), 
        new NodeData("q1",
        false,
        true),
        "custom");

        ReactFlowEdge q0Toq1 = new ReactFlowEdge("e0",
        "0",
        "1",
        "a",
        "default"
        );

        nodes.add(q0);
        nodes.add(q1);
        edges.add(q0Toq1);

        return new ReactFlowGraph("NFA", edges, nodes);
    }


    public static ReactFlowGraph reactFlowGraphWithNoStartingState(){
        List<ReactFlowEdge> edges = new ArrayList<>();
        List<ReactFlowNode> nodes = new ArrayList<>();

        ReactFlowNode q0 = new ReactFlowNode("0", 
        new Position(100,100), 
        new NodeData("q0",
        false,
        false),
        "custom");

        ReactFlowNode q1 = new ReactFlowNode("1", 
        new Position(100,100), 
        new NodeData("q1",
        false,
        true),
        "custom");

        ReactFlowEdge q0Toq1 = new ReactFlowEdge("e0",
        "0",
        "1",
        "a",
        "default"
        );

        nodes.add(q0);
        nodes.add(q1);
        edges.add(q0Toq1);

        return new ReactFlowGraph("NFA", edges, nodes);
    }


    public static ReactFlowGraph reactFlowGraphWithMultipleStartingStates(){
        List<ReactFlowEdge> edges = new ArrayList<>();
        List<ReactFlowNode> nodes = new ArrayList<>();

        ReactFlowNode q0 = new ReactFlowNode("0", 
        new Position(100,100), 
        new NodeData("q0",
        true,
        false),
        "custom");

        ReactFlowNode q1 = new ReactFlowNode("1", 
        new Position(100,100), 
        new NodeData("q1",
        true,
        true),
        "custom");

        ReactFlowEdge q0Toq1 = new ReactFlowEdge("e0",
        "0",
        "1",
        "a",
        "default"
        );

        nodes.add(q0);
        nodes.add(q1);
        edges.add(q0Toq1);

        return new ReactFlowGraph("NFA", edges, nodes);
    }

    public static ReactFlowGraph reactFlowGraphWithANullEdge(){
        List<ReactFlowEdge> edges = new ArrayList<>();
        List<ReactFlowNode> nodes = new ArrayList<>();

        ReactFlowNode q0 = new ReactFlowNode("0", 
        new Position(100,100), 
        new NodeData("q0",
        true,
        false),
        "custom");

        ReactFlowNode q1 = new ReactFlowNode("1", 
        new Position(100,100), 
        new NodeData("q1",
        false,
        true),
        "custom");

        ReactFlowEdge q0Toq1 = null;

        nodes.add(q0);
        nodes.add(q1);
        edges.add(q0Toq1);

        return new ReactFlowGraph("NFA", edges, nodes);
    }


    public static ReactFlowGraph reactFlowGraphWithAnEdgeWithNullId(){
        List<ReactFlowEdge> edges = new ArrayList<>();
        List<ReactFlowNode> nodes = new ArrayList<>();

        ReactFlowNode q0 = new ReactFlowNode("0", 
        new Position(100,100), 
        new NodeData("q0",
        true,
        false),
        "custom");

        ReactFlowNode q1 = new ReactFlowNode("1", 
        new Position(100,100), 
        new NodeData("q1",
        false,
        true),
        "custom");

        ReactFlowEdge q0Toq1 = new ReactFlowEdge(null,
        "0",
        "1",
        "a",
        "default"
        );

        nodes.add(q0);
        nodes.add(q1);
        edges.add(q0Toq1);


        return new ReactFlowGraph("NFA", edges, nodes);
    }


    public static ReactFlowGraph reactFlowGraphWithADupeEdge(){
        List<ReactFlowEdge> edges = new ArrayList<>();
        List<ReactFlowNode> nodes = new ArrayList<>();

        ReactFlowNode q0 = new ReactFlowNode("0", 
        new Position(100,100), 
        new NodeData("q0",
        true,
        false),
        "custom");

        ReactFlowNode q1 = new ReactFlowNode("1", 
        new Position(100,100), 
        new NodeData("q1",
        false,
        true),
        "custom");

        ReactFlowEdge q0Toq1 = new ReactFlowEdge("e0",
        "0",
        "1",
        "a",
        "default"
        );

        nodes.add(q0);
        nodes.add(q1);
        edges.add(q0Toq1);
        edges.add(q0Toq1);

        return new ReactFlowGraph("NFA", edges, nodes);
    }


    public static ReactFlowGraph reactFlowGraphWithEdgeWithNullSource(){
        List<ReactFlowEdge> edges = new ArrayList<>();
        List<ReactFlowNode> nodes = new ArrayList<>();

        ReactFlowNode q0 = new ReactFlowNode("0", 
        new Position(100,100), 
        new NodeData("q0",
        true,
        false),
        "custom");

        ReactFlowNode q1 = new ReactFlowNode("1", 
        new Position(100,100), 
        new NodeData("q1",
        false,
        true),
        "custom");

        ReactFlowEdge q0Toq1 = new ReactFlowEdge("e0",
        null,
        "1",
        "a",
        "default"
        );

        nodes.add(q0);
        nodes.add(q1);
        edges.add(q0Toq1);

        return new ReactFlowGraph("NFA", edges, nodes);
    }


    public static ReactFlowGraph reactFlowGraphWithEdgeWithNullTarget(){
        List<ReactFlowEdge> edges = new ArrayList<>();
        List<ReactFlowNode> nodes = new ArrayList<>();

              ReactFlowNode q0 = new ReactFlowNode("0", 
        new Position(100,100), 
        new NodeData("q0",
        true,
        false),
        "custom");

        ReactFlowNode q1 = new ReactFlowNode("1", 
        new Position(100,100), 
        new NodeData("q1",
        false,
        true),
        "custom");

        ReactFlowEdge q0Toq1 = new ReactFlowEdge("e0",
        "0",
        null,
        "a",
        "default"
        );

        nodes.add(q0);
        nodes.add(q1);
        edges.add(q0Toq1);

        return new ReactFlowGraph("NFA", edges, nodes);
    }


    public static ReactFlowGraph reactFlowGraphWithEdgeWithUnknownSource(){
        List<ReactFlowEdge> edges = new ArrayList<>();
        List<ReactFlowNode> nodes = new ArrayList<>();

      ReactFlowNode q0 = new ReactFlowNode("0", 
        new Position(100,100), 
        new NodeData("q0",
        true,
        false),
        "custom");

        ReactFlowNode q1 = new ReactFlowNode("1", 
        new Position(100,100), 
        new NodeData("q1",
        false,
        true),
        "custom");

        ReactFlowEdge q0Toq1 = new ReactFlowEdge("e0",
        "3",
        "1",
        "a",
        "default"
        );

        nodes.add(q0);
        nodes.add(q1);
        edges.add(q0Toq1);
        return new ReactFlowGraph("NFA", edges, nodes);
    }


    public static ReactFlowGraph reactFlowGraphWithEdgeWithUnknownTarget(){    
        List<ReactFlowEdge> edges = new ArrayList<>();
        List<ReactFlowNode> nodes = new ArrayList<>();

        ReactFlowNode q0 = new ReactFlowNode("0", 
        new Position(100,100), 
        new NodeData("q0",
        true,
        false),
        "custom");

        ReactFlowNode q1 = new ReactFlowNode("1", 
        new Position(100,100), 
        new NodeData("q1",
        false,
        true),
        "custom");

        ReactFlowEdge q0Toq1 = new ReactFlowEdge("e0",
        "0",
        "3",
        "a",
        "default"
        );

        nodes.add(q0);
        nodes.add(q1);
        edges.add(q0Toq1);

        return new ReactFlowGraph("NFA", edges, nodes);
    }


     public static ReactFlowGraph reactFlowGraphWithEdgeWithNullEdgeLabel(){
        List<ReactFlowEdge> edges = new ArrayList<>();
        List<ReactFlowNode> nodes = new ArrayList<>();

        ReactFlowNode q0 = new ReactFlowNode("0", 
        new Position(100,100), 
        new NodeData("q0",
        true,
        false),
        "custom");

        ReactFlowNode q1 = new ReactFlowNode("1", 
        new Position(100,100), 
        new NodeData("q1",
        false,
        true),
        "custom");

        ReactFlowEdge q0Toq1 = new ReactFlowEdge("e0",
        "0",
        "1",
        null,
        "default"
        );

        nodes.add(q0);
        nodes.add(q1);
        edges.add(q0Toq1);


        return new ReactFlowGraph("NFA", edges, nodes);
    }


    public static ReactFlowGraph reactFlowGraphWithEdgeWithEmptyLabel(){
        List<ReactFlowEdge> edges = new ArrayList<>();
        List<ReactFlowNode> nodes = new ArrayList<>();

        ReactFlowNode q0 = new ReactFlowNode("0", 
        new Position(100,100), 
        new NodeData("q0",
        true,
        false),
        "custom");

        ReactFlowNode q1 = new ReactFlowNode("1", 
        new Position(100,100), 
        new NodeData("q1",
        false,
        true),
        "custom");

        ReactFlowEdge q0Toq1 = new ReactFlowEdge("e0",
        "0",
        "1",
        "",
        "default"
        );

        nodes.add(q0);
        nodes.add(q1);
        edges.add(q0Toq1);


        return new ReactFlowGraph("NFA", edges, nodes);
    }

    
    public static ReactFlowGraph reactFlowGraphWithEdgeWithCommaSeparatedSymbolsInTransition(){
        List<ReactFlowEdge> edges = new ArrayList<>();
        List<ReactFlowNode> nodes = new ArrayList<>();

        ReactFlowNode q0 = new ReactFlowNode("0", 
        new Position(100,100), 
        new NodeData("q0",
        true,
        false),
        "custom");

        ReactFlowNode q1 = new ReactFlowNode("1", 
        new Position(100,100), 
        new NodeData("q1",
        false,
        true),
        "custom");

        ReactFlowEdge q0Toq1 = new ReactFlowEdge("e0",
        "0",
        "1",
        "a,b,c",
        "default"
        );

        nodes.add(q0);
        nodes.add(q1);
        edges.add(q0Toq1);


        return new ReactFlowGraph("NFA", edges, nodes);
    }


    public static ReactFlowGraph reactFlowGraphWithEdgeContainingEpsilon(){
        List<ReactFlowEdge> edges = new ArrayList<>();
        List<ReactFlowNode> nodes = new ArrayList<>();

        ReactFlowNode q0 = new ReactFlowNode("0", 
        new Position(100,100), 
        new NodeData("q0",
        true,
        false),
        "custom");

        ReactFlowNode q1 = new ReactFlowNode("1", 
        new Position(100,100), 
        new NodeData("q1",
        false,
        true),
        "custom");

        ReactFlowEdge q0Toq1 = new ReactFlowEdge("e0",
        "0",
        "1",
        "ε",
        "default"
        );

        nodes.add(q0);
        nodes.add(q1);
        edges.add(q0Toq1);

        return new ReactFlowGraph("NFA", edges, nodes);
    }


    public static ReactFlowGraph reactFlowGraphWithEdgeContainingMultipleSymbolsPerTransition(){
        List<ReactFlowEdge> edges = new ArrayList<>();
        List<ReactFlowNode> nodes = new ArrayList<>();

        ReactFlowNode q0 = new ReactFlowNode("0", 
        new Position(100,100), 
        new NodeData("q0",
        true,
        false),
        "custom");

        ReactFlowNode q1 = new ReactFlowNode("1", 
        new Position(100,100), 
        new NodeData("q1",
        false,
        true),
        "custom");

        ReactFlowEdge q0Toq1 = new ReactFlowEdge("e0",
        "0",
        "1",
        "ab,cd",
        "default"
        );

        nodes.add(q0);
        nodes.add(q1);
        edges.add(q0Toq1);

        return new ReactFlowGraph("NFA", edges, nodes);
    }


    public static ReactFlowGraph reactFlowGraphWithInvalidSymbolsInATransition(){
        List<ReactFlowEdge> edges = new ArrayList<>();
        List<ReactFlowNode> nodes = new ArrayList<>();

        ReactFlowNode q0 = new ReactFlowNode("0", 
        new Position(100,100), 
        new NodeData("q0",
        true,
        false),
        "custom");

        ReactFlowNode q1 = new ReactFlowNode("1", 
        new Position(100,100), 
        new NodeData("q1",
        false,
        true),
        "custom");

        ReactFlowEdge q0Toq1 = new ReactFlowEdge("e0",
        "0",
        "1",
        "&",
        "default"
        );

        nodes.add(q0);
        nodes.add(q1);
        edges.add(q0Toq1);

        return new ReactFlowGraph("NFA", edges, nodes);
    }


    public static ReactFlowGraph reactFlowGraphWithASelfLoop(){
        List<ReactFlowEdge> edges = new ArrayList<>();
        List<ReactFlowNode> nodes = new ArrayList<>();

        ReactFlowNode q0 = new ReactFlowNode("0", 
        new Position(100,100), 
        new NodeData("q0",
        true,
        true),
        "custom");

        ReactFlowEdge q0Toq0 = new ReactFlowEdge("e0",
        "0",
        "0",
        "a",
        "default"
        );

        nodes.add(q0);
        edges.add(q0Toq0);

        return new ReactFlowGraph("NFA", edges, nodes);
    }


    public static ReactFlowGraph reactFlowGraphWithNoEdges(){
        List<ReactFlowEdge> edges = new ArrayList<>();
        List<ReactFlowNode> nodes = new ArrayList<>();

        ReactFlowNode q0 = new ReactFlowNode("0", 
        new Position(100,100), 
        new NodeData("q0",
        true,
        true),
        "custom");

        nodes.add(q0);

        return new ReactFlowGraph("NFA", edges, nodes);
    }


    public static ReactFlowGraph reactFlowGraphWithAnIsolatedNode(){
        List<ReactFlowEdge> edges = new ArrayList<>();
        List<ReactFlowNode> nodes = new ArrayList<>();

        ReactFlowNode q0 = new ReactFlowNode("0", 
        new Position(100,100), 
        new NodeData("q0",
        true,
        false),
        "custom");

        ReactFlowNode q1 = new ReactFlowNode("1", 
        new Position(100,100), 
        new NodeData("q1",
        false,
        true),
        "custom");

        ReactFlowNode q2 = new ReactFlowNode("2", 
        new Position(100,100), 
        new NodeData("q2",
        false,
        false),
        "custom");

        ReactFlowEdge q0Toq1 = new ReactFlowEdge("e0",
        "0",
        "1",
        "a",
        "default"
        );

        nodes.add(q0);
        nodes.add(q1);
        nodes.add(q2);
        edges.add(q0Toq1);

        return new ReactFlowGraph("NFA", edges, nodes);
    }

    public static ReactFlowGraph reactFlowGraphEpsilonOnly(){
        List<ReactFlowEdge> edges = new ArrayList<>();
        List<ReactFlowNode> nodes = new ArrayList<>();

        ReactFlowNode q0 = new ReactFlowNode("0", 
        new Position(100,100), 
        new NodeData("q0",
        true,
        true),
        "custom");

        nodes.add(q0);

        return new ReactFlowGraph("NFA", edges, nodes);
    }



    public static ReactFlowGraph reactFlowGraphComplex(){
        List<ReactFlowEdge> edges = new ArrayList<>();
        List<ReactFlowNode> nodes = new ArrayList<>();

        ReactFlowNode q0 = new ReactFlowNode("0", 
        new Position(100,100), 
        new NodeData("q0",
        true,
        false),
        "custom");

        ReactFlowNode q1 = new ReactFlowNode("1", 
        new Position(100,100), 
        new NodeData("q1",
        false,
        false),
        "custom");

        ReactFlowNode q2 = new ReactFlowNode("2", 
        new Position(100,100), 
        new NodeData("q2",
        false,
        false),
        "custom");


        ReactFlowNode q3 = new ReactFlowNode("3", 
        new Position(100,100), 
        new NodeData("q3",
        false,
        false),
        "custom");

        ReactFlowNode q4 = new ReactFlowNode("4", 
        new Position(100,100), 
        new NodeData("q4",
        false,
        false),
        "custom");


        ReactFlowNode q5 = new ReactFlowNode("5", 
        new Position(100,100), 
        new NodeData("q5",
        false,
        true),
        "custom");

        ReactFlowEdge e0 = new ReactFlowEdge("e0","0","1","a","default");
        ReactFlowEdge e1 = new ReactFlowEdge("e1","0","3","a","default");
        ReactFlowEdge e2 = new ReactFlowEdge("e2","0","2","b","default");
        ReactFlowEdge e3 = new ReactFlowEdge("e3","1","1","a","default");
        ReactFlowEdge e4 = new ReactFlowEdge("e4","1","3","a","default");
        ReactFlowEdge e5 = new ReactFlowEdge("e5","1","2","b","default");
        ReactFlowEdge e6 = new ReactFlowEdge("e6","2","1","a","default");
        ReactFlowEdge e7 = new ReactFlowEdge("e7","2","3","a","default");
        ReactFlowEdge e8 = new ReactFlowEdge("e8","2","2","b","default");
        ReactFlowEdge e9 = new ReactFlowEdge("e9","3","4","b","default");
        ReactFlowEdge e10 = new ReactFlowEdge("e10","4","5","b","default");

        nodes.add(q0);
        nodes.add(q1);
        nodes.add(q2);
        nodes.add(q3);
        nodes.add(q4);
        nodes.add(q5);
        edges.add(e0);
        edges.add(e1);
        edges.add(e2);
        edges.add(e3);
        edges.add(e4);
        edges.add(e5);
        edges.add(e6);
        edges.add(e7);
        edges.add(e8);
        edges.add(e9);
        edges.add(e10);

        return new ReactFlowGraph("NFA", edges, nodes);
    }

}
