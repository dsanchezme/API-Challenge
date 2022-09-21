package helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SystemVariables {

    private String apiKey;
    private String username;
    private String password;

    private final Logger logger = LogManager.getLogger("system-variables");

    public SystemVariables(){
        setApiKey();
        setUsername();
        setPassword();
    }

    public String getApiKey() {
        return apiKey;
    }

    private void setApiKey() {
        this.apiKey = System.getenv("TMDB_API_KEY");
        isVariableNull(this.apiKey, "TMDB_API_KEY");
    }

    public String getUsername() {
        return username;
    }

    private void setUsername() {
        this.username = System.getenv("TMDB_VALID_USERNAME");
        isVariableNull(this.username, "TMDB_VALID_USERNAME");
    }

    public String getPassword() {
        return password;
    }

    private void setPassword() {
        this.password = System.getenv("TMDB_VALID_PASSWORD");
        isVariableNull(this.password, "TMDB_VALID_PASSWORD");
    }

    private void isVariableNull(String variable, String variableName){
        if (variable == null){
            logger.warn(variableName + " was not found in the system environment variables.");
        }
    }
}
