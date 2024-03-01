package kr.kanzi.postsvc.domain.stock;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "market")
@ToString

public class Market {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime baseDate;
    private String mktType;
    private Float open;
    private Float high;
    private Float low;
    private Float close;
    private Long volume;
    private Long amount;
    private Long value;
    private Long fValue;
    private Float per;
    private Float pbr;
    private Float dy;
    private Long credit;
    private Long deposit;
    private Float adr;

}
