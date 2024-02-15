package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Current {
    private float temp_c;

//    float getTemp_c(){
//        return temp_c;
//    }

//    void setTemp_c(float temp_c){
//        this.temp_c = temp_c;
//    }
}
