package fr.techcrud.minibank.domain.model;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class LedgerEntry {
    private final UUID id;
    private final UUID transferId;
    private final UUID accountId;
    private final EntryType type;
    private final Money amount;
    private final Money balanceAfterTransfer;
    private final Instant createdAt;

    public LedgerEntry(UUID id, UUID transferId, UUID accountId,  EntryType type, Money amount, Money balanceAfterTransfer, Instant createdAt) {
        this.id = id;
        this.transferId = transferId;
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.balanceAfterTransfer = balanceAfterTransfer;
        this.createdAt = createdAt;

    }

    public static LedgerEntry createLedgerEntry(UUID transferId, UUID accountId, EntryType type, Money amount, Money balanceAfterTransfer) {
        return new LedgerEntry(UUID.randomUUID(), transferId, accountId, type, amount, balanceAfterTransfer, Instant.now());
    }
}
