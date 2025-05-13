package brzeph.backend.spring.java_spring_demo.dto.serverConfig;

import java.time.Instant;
import java.util.Objects;

public class ServerConfigDTO {

    private String key;
    private String value;
    private String description;
    private Instant updatedAt;

    public ServerConfigDTO() {
    }

    public ServerConfigDTO(String key, String value, String description, Instant updatedAt) {
        this.key = key;
        this.value = value;
        this.description = description;
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "ServerConfigDTO{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", description='" + description + '\'' +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerConfigDTO that = (ServerConfigDTO) o;
        return Objects.equals(key, that.key) && Objects.equals(value, that.value) && Objects.equals(description, that.description) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value, description, updatedAt);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
