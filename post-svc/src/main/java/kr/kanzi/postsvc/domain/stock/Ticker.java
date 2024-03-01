package kr.kanzi.postsvc.domain.stock;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "ticker")
public class Ticker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String stockCode;
    private LocalDateTime baseDate;
    private String fsType;

    private float pbr;
    private float per;
    private float por;
    private float psr;
    private float dy;
    private long value;
    private long shares;
    private long volume;
    private long close;

}