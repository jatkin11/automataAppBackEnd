package com.jakeatkins.automataappbackend.controllers;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jakeatkins.automataappbackend.dto.AutomataWordTest;
import com.jakeatkins.automataappbackend.dto.ReactFlowGraph;
import com.jakeatkins.automataappbackend.dto.RegexString;
import com.jakeatkins.automataappbackend.dto.RegexWordTest;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphValidGraph;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphWithADupeEdge;
import static com.jakeatkins.automataappbackend.utilities.TestReactFlowGraphCreator.reactFlowGraphWithEmptyNodesList;
import static com.jakeatkins.automataappbackend.utilities.TestRegexStringCreator.RegexStringWithBlankRegex;
import static com.jakeatkins.automataappbackend.utilities.TestRegexStringCreator.RegexStringWithNullRegex;
import static com.jakeatkins.automataappbackend.utilities.TestRegexStringCreator.simpleInvalidRegexString;
import static com.jakeatkins.automataappbackend.utilities.TestRegexStringCreator.simpleValidRegexString;

import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class AutomataControllerTester {
    
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void convertToDFaValidRequestReturnsOK() throws Exception{
        ReactFlowGraph rfg = reactFlowGraphValidGraph();

        mockMvc.perform(post("/api/automata/convert-to-dfa")
        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(rfg))).andExpect(status().isOk());

    }

    @Test
    void convertToDfaInvalidGraphReturnsBadRequest() throws Exception {
        ReactFlowGraph rfg = reactFlowGraphWithADupeEdge();

        mockMvc.perform(post("/api/automata/convert-to-dfa")
        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(rfg))).andExpect(status().isBadRequest());
    }

    @Test
    void convertToDfaEmptyGraphReturnsBadRequest() throws Exception {
        ReactFlowGraph rfg = reactFlowGraphWithEmptyNodesList();

        mockMvc.perform(post("/api/automata/convert-to-dfa")
        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(rfg))).andExpect(status().isBadRequest());
    }

    @Test
    void regexToNfaValidRequestReturnsOk() throws Exception {
        RegexString rs = simpleValidRegexString();

        mockMvc.perform(post("/api/automata/convert-to-nfa")
        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(rs))).andExpect(status().isOk());
    }
    
    @Test
    void regexToNfaInvalidRequestReturnsBadRequest() throws Exception {
        RegexString rs = simpleValidRegexString();

        mockMvc.perform(post("/api/automata/convert-to-nfa")
        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(rs))).andExpect(status().isOk());
    }
    
    @Test
    void regexToNfaNullRegexReturnsBadRequest() throws Exception {
        RegexString rs = simpleInvalidRegexString();

        mockMvc.perform(post("/api/automata/convert-to-nfa")
        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(rs))).andExpect(status().isBadRequest());
    }
        
    @Test
    void regexToNfa() throws Exception {
        RegexString rs = RegexStringWithBlankRegex();

        mockMvc.perform(post("/api/automata/convert-to-nfa")
        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(rs))).andExpect(status().isBadRequest());
    }
        
    @Test
    void regexToNfaNullRegexInRegexStringReturnsBadRequest() throws Exception {
        RegexString rs = RegexStringWithNullRegex();

        mockMvc.perform(post("/api/automata/convert-to-nfa")
        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(rs))).andExpect(status().isBadRequest());
    }
        
    @Test
    void automataToRegexValidRequestReturnsOk() throws Exception {
        ReactFlowGraph rfg = reactFlowGraphValidGraph();

        mockMvc.perform(post("/api/automata/convert-to-regex-string")
        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(rfg))).andExpect(status().isOk());

    }
    
    @Test
    void automataToRegexInvalidGraphReturnsBadRequest() throws Exception {
        ReactFlowGraph rfg = reactFlowGraphWithEmptyNodesList();

        mockMvc.perform(post("/api/automata/convert-to-nfa")
        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(rfg))).andExpect(status().isBadRequest());
    }
        
    @Test
    void automataWordTestValidTestRequestReturnsOk() throws Exception {
        ReactFlowGraph rfg = reactFlowGraphValidGraph();
        AutomataWordTest awt = new AutomataWordTest(rfg, "a");

        mockMvc.perform(post("/api/automata/test-word-on-automata")
        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(awt))).andExpect(status().isOk());
    }
        
    @Test
    void automataWordTestInvalidTestRequestReturnsBadRequest() throws Exception {
        ReactFlowGraph rfg = reactFlowGraphValidGraph();
        AutomataWordTest awt = new AutomataWordTest(rfg, null);

        mockMvc.perform(post("/api/automata/test-word-on-automata")
        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(awt))).andExpect(status().isBadRequest());

    }
        
    @Test
    void regexWordTestValidTestRequestReturnsOk() throws Exception {
        RegexWordTest rwt = new RegexWordTest("a", "a");

        mockMvc.perform(post("/api/automata/test-word-on-regex-string")
        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(rwt))).andExpect(status().isOk());
    }
        
    @Test
    void regexWordTestInvalidTestRequestReturnsBadRequest() throws Exception {
        RegexWordTest rwt = new RegexWordTest("a", null);

        mockMvc.perform(post("/api/automata/test-word-on-regex-string")
        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(rwt))).andExpect(status().isBadRequest());
    }
        
    @Test
    void dfaMinimiserInvalidRequestReturnsBadRequest() throws Exception {
        ReactFlowGraph rfg = reactFlowGraphWithEmptyNodesList();

        mockMvc.perform(post("/api/automata/minimise-dfa")
        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(rfg))).andExpect(status().isBadRequest());

    }
    @Test
    void dfaMinimiserValidRequestReturnsOk() throws Exception {
      ReactFlowGraph rfg = reactFlowGraphValidGraph();

        mockMvc.perform(post("/api/automata/minimise-dfa")
        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(rfg))).andExpect(status().isOk());


    }
        

}
