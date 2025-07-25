package deserailizationprocess;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public class FreightInvoiceResponse {
        private boolean success;
        private DataNode data;

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class DataNode {
            @JsonProperty("FreightInvoiceId")
            private String FreightInvoiceId;

            @JsonProperty("CreatedSourceTypeId")
            private CreatedSourceTypeId CreatedSourceTypeId;

            @JsonProperty("RequestedTotalOnInvoice")
            private int RequestedTotalOnInvoice;

            private HeaderSummary HeaderSummary;
            private List<FreightShipment> FreightShipment;

        }

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class CreatedSourceTypeId {
            private String Name;
        }

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class HeaderSummary {
            private int PlannedTotal;
            private int ApprovedTotal;
            private int BilledTotal;
            private int BalanceDueTotal;
        }

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public  static class FreightShipment {
            private String ShipmentId;
            private String ModeId;
            private FreightShpAddlAttrs FreightShpAddlAttrs;
        }

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class FreightShpAddlAttrs {
            private int PlannedCost;
            private int InvoicedAmount;
        }
    }

