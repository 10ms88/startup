package com.company.startup.core.service;

public interface WbService {

    String NAME = "WbService";

    String STATISTIC_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2Nlc3NJRCI6IjI5NzAzNGRkLTYxOWItNGZmYy05NTU5LWNhMjM4MDc4NjZkMyJ9.riysezuNVTNJqFW1Ke4HhIW9XRybr5NnKg57jOmU_8s";

    String STANDARD_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2Nlc3NJRCI6ImMzMjZhMjFiLThmZGEtNDQzYy1iMzZjLTIxNmE4NGRlZDkzMCJ9.vxenbG0QnkSvNm8oEyQPtA-QKaPIdB8il1tym3CAfxs";

    String getStatistic(boolean iaAll);

    String getOrders();

    String getStocks();

}

