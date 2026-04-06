package com.ade.algorithms;

import com.ade.models.Input;
import com.ade.models.Result;

public interface Algorithm {

    String getName();

    Result solve(Input input);
}