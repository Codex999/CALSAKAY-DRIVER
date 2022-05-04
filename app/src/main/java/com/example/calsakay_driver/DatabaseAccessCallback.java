package com.example.calsakay_driver;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface DatabaseAccessCallback {
    void QueryResponse(List<String[]> data);
}
