/**
 * This file is part of alf.io.
 *
 * alf.io is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * alf.io is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with alf.io.  If not, see <http://www.gnu.org/licenses/>.
 */
package alfio.model.transaction.webhook;

import alfio.model.transaction.TransactionWebhookPayload;
import alfio.model.transaction.provider.RevolutTransactionDescriptor;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

public class RevolutWebhookPayload implements TransactionWebhookPayload {

    private final String type;
    private final ZonedDateTime timestamp;
    private final RevolutTransactionDescriptor payload;
    private String reservationId;

    @JsonCreator
    public RevolutWebhookPayload(@JsonProperty("event") String type,
                                 @JsonProperty("timestamp") ZonedDateTime timestamp,
                                 @JsonProperty("data") RevolutTransactionDescriptor payload) {
        this.type = type;
        this.timestamp = timestamp;
        this.payload = payload;
    }


    @Override
    public RevolutTransactionDescriptor getPayload() {
        return payload;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getReservationId() {
        return reservationId;
    }

    @Override
    public Status getStatus() {
        return payload.getState().equals("completed") ? Status.SUCCESS : Status.FAILURE;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public RevolutWebhookPayload attachToReservation(String reservationId) {
        this.reservationId = reservationId;
        return this;
    }
}