package fr.techcrud.minibank.domain.model;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class Transfer {
    private final UUID id;
    private final UUID sourceAccountId;
    private final UUID targetAccountId;
    private final Money amount;
    private final String idempotencyKey;
    private final String label;
    private TransferStatus status;
    private String rejectionReason;
    private final Instant createdAt;

    public Transfer(UUID id, UUID sourceAccountId, UUID targetAccountId, Money amount, String idempotencyKey, String label, TransferStatus status, String rejectionReason, Instant createdAt ) {
        this.id = id;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.amount = amount;
        this.idempotencyKey = idempotencyKey;
        this.label = label;
        this.status = status;
        this.rejectionReason = rejectionReason;
        this.createdAt = createdAt;

    }

    public static Transfer createTransfeer(UUID sourceAccountId, UUID targetAccountId, Money amount, String idempotencyKey, String label, TransferStatus status, Instant createdAt) {
        TransferStatus createdStatus = TransferStatus.PENDING;
        Instant now = Instant.now();


        return new Transfer(
                UUID.randomUUID(),
                sourceAccountId,
                targetAccountId,
                amount,
                idempotencyKey,
                label,
                createdStatus,
                null,
                now);

    }

}
