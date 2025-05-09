package brzeph.backend.spring.java_spring_demo.services;

import brzeph.backend.spring.java_spring_demo.entities.audit.AuditLog;
import brzeph.backend.spring.java_spring_demo.entities.users.User;
import brzeph.backend.spring.java_spring_demo.repositories.AuditLogRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public void log(User user, String action, String metadata) {
        ZonedDateTime zoned = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
        Instant zonedInstant = zoned.toInstant();
        AuditLog log = new AuditLog(user, action, zonedInstant, metadata);
        auditLogRepository.save(log);
    }

    public List<AuditLog> getLogsForUser(User user) {
        return auditLogRepository.findByUserTypeAndUserId("USER", user.getId());
    }
}
