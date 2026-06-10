package co.edu.udea.certificacion.gestionfinanciera.config;


public final class ApiConfig {

    public static final String BASE_URL = "http://localhost:8080";
    public static final String BASE_PATH = "/api/v1";

    public static final String AUTH_REGISTER   = BASE_PATH + "/auth/register";
    public static final String AUTH_LOGIN      = BASE_PATH + "/auth/login";
    public static final String CATEGORIAS      = BASE_PATH + "/categorias";
    public static final String TRANSACCIONES   = BASE_PATH + "/transacciones";
    public static final String BALANCE_MES     = BASE_PATH + "/balance/mes-actual";
    public static final String BALANCE_PERIODO = BASE_PATH + "/balance";
    public static final String PRESUPUESTOS    = BASE_PATH + "/presupuestos";
    
}
