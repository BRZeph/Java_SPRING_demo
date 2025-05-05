package brzeph.backend.spring.java_spring_demo.entities.audit;

import brzeph.backend.spring.java_spring_demo.entities.users.User;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
@Entity
@Table(name = "tb_audit_logs")
public class AuditLog implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 100)
    private String action;

    @Column(nullable = false)
    private Instant timestamp;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String metadata;

    public AuditLog() {
    }

    public AuditLog(User user, String action, Instant timestamp, String metadata) {
        this.user = user;
        this.action = action;
        this.timestamp = timestamp;
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "AuditLog{" +
                "id=" + id +
                ", user=" + user +
                ", action='" + action + '\'' +
                ", timestamp=" + timestamp +
                ", metadata='" + metadata + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuditLog auditLog = (AuditLog) o;
        return Objects.equals(id, auditLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}
