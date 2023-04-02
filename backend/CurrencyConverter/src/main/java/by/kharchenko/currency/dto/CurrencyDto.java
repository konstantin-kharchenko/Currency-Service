package by.kharchenko.currency.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
public class CurrencyDto {

    @JsonProperty("Cur_Abbreviation")
    private String curAbbreviation;

    @JsonProperty("Cur_OfficialRate")
    private BigDecimal curOfficialRate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyDto that = (CurrencyDto) o;
        return Objects.equals(curAbbreviation, that.curAbbreviation) && Objects.equals(curOfficialRate, that.curOfficialRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(curAbbreviation, curOfficialRate);
    }

    @Override
    public String toString() {
        return "CurrencyDto{" +
                "curAbbreviation='" + curAbbreviation + '\'' +
                ", curOfficialRate=" + curOfficialRate +
                '}';
    }
}
