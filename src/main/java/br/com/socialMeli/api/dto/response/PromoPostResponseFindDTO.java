package br.com.socialMeli.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromoPostResponseFindDTO {

    private Long idPost;

    private Date date;

    private List<ProductResponseFindDTO> detail;

    private Long category;

    private Double price;

    private Boolean hasPromo;

    private Double discount;
}
