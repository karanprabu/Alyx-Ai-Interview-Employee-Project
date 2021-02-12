package com.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.annotation.JsonProperty;
 
@XmlRootElement(name = "error")
public class ServeiceResponseDTO {
	
	 public ServeiceResponseDTO(String message, List<String> desc) {
	        super();
	        this.message = message;
	        this.description = desc;
	    }
	 
	    @JsonProperty("Message")
	    private String message;
	  
	    @JsonProperty("Decription")
	    private List<String> description;
	 
	    //Getter and setters

}
