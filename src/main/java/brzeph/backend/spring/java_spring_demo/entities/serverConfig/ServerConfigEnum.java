package brzeph.backend.spring.java_spring_demo.entities.serverConfig;

public enum ServerConfigEnum {

//    *************** ALERT ******************** ALERT *************** ALERT ******************** ALERT ***************
//    DO NOT change the "key" parameters of this enum, IT WILL CAUSE ISSUES IN MYSQL DATABASE.
//    *************** ALERT ******************** ALERT *************** ALERT ******************** ALERT ***************

    SEEDED_DB("Seeded_db", "Banco está com informações básicas"),
    DEV_DB("Dev_db", "O banco está em desenvolvimento"),

//    *************** ALERT ******************** ALERT *************** ALERT ******************** ALERT ***************
//    DO NOT change the "key" parameters of this enum, IT WILL CAUSE ISSUES IN MYSQL DATABASE.
//    *************** ALERT ******************** ALERT *************** ALERT ******************** ALERT ***************

    POSITIVE("X", "Bool_positivo"),
    NEGATIVE("", "Bool_negativo");

//    *************** ALERT ******************** ALERT *************** ALERT ******************** ALERT ***************
//    DO NOT change the "key" parameters of this enum, IT WILL CAUSE ISSUES IN MYSQL DATABASE.
//    *************** ALERT ******************** ALERT *************** ALERT ******************** ALERT ***************

    private final String key;
    private final String description;

    ServerConfigEnum(String key, String description) {
        this.key = key;
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }
}
