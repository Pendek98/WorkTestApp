package com.example.demo;

import java.io.Serializable;
import java.net.URL;
import java.util.List;

import com.example.demo.Owner;
import com.example.demo.Branch;
public class Repository implements Serializable{

      
      String name;
      Owner owner;
      boolean fork;
      List<Branch> branch;
      String updated_at;

      
      public List<Branch> getBranch() {
        return branch;
      }
      public void setBranch(List<Branch> branch) {
        this.branch = branch;
      }

      public String getUpdated_at() {
        return updated_at;
      }
      public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
      }
      public String getName() {
        return name;
      }
      public void setName(String name) {
        this.name = name;
      }
      
      public Owner getOwner() {
        return owner;
      }
      public void setOwner(Owner owner) {
        this.owner = owner;
      }
      public boolean isFork() {
        return fork;
      }
      public void setFork(boolean fork) {
        this.fork = fork;
      }
      

          
}
