package com.feiwanghub.subdataflownew.GraphComputation;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class GraphRecord implements Serializable {

    private int id;
    private int rec;

}
