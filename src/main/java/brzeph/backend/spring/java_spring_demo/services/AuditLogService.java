package brzeph.backend.spring.java_spring_demo.services;

import brzeph.backend.spring.java_spring_demo.entities.audit.AuditLog;
import brzeph.backend.spring.java_spring_demo.entities.users.User;
import brzeph.backend.spring.java_spring_demo.repositories.AuditLogRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public void log(User user, String action, String metadata) {
        AuditLog log = new AuditLog(user, action, Instant.now(), metadata);
        auditLogRepository.save(log);
    }
}
