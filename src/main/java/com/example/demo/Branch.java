package com.example.demo;

import java.io.Serializable;
import com.example.demo.commit;
public class Branch implements Serializable{

    String name;
    commit commit;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public commit getCommit() {
        return commit;
    }
    public void setCommit(commit commit) {
        this.commit = commit;
    }
    
    
}
