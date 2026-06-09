package com.itbaizhan;

import dev.langchain4j.code.graalvm.GraalVmJavaScriptExecutionEngine;
import org.junit.jupiter.api.Test;

public class GraalVMTest {

    @Test
    public void  test(){
        GraalVmJavaScriptExecutionEngine engine = new GraalVmJavaScriptExecutionEngine();
        String code = """
                function sumaa(a,b){
                  return a + b ;
                }
                sumaa(10,20)
                """;
        String execute = engine.execute(code);
        System.out.println(execute);


    }
}
