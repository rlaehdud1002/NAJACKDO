package com.najackdo.server.domain.rental.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRental is a Querydsl query type for Rental
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRental extends EntityPathBase<Rental> {

    private static final long serialVersionUID = -1247719512L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRental rental = new QRental("rental");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.najackdo.server.domain.user.entity.QUser loner;

    public final NumberPath<Integer> rentalCost = createNumber("rentalCost", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> rentalEndDate = createDateTime("rentalEndDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> rentalPeriod = createNumber("rentalPeriod", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> rentalStartDate = createDateTime("rentalStartDate", java.time.LocalDateTime.class);

    public final QRentalSchedule schedule;

    public final EnumPath<RentalStatus> status = createEnum("status", RentalStatus.class);

    public QRental(String variable) {
        this(Rental.class, forVariable(variable), INITS);
    }

    public QRental(Path<? extends Rental> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRental(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRental(PathMetadata metadata, PathInits inits) {
        this(Rental.class, metadata, inits);
    }

    public QRental(Class<? extends Rental> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.loner = inits.isInitialized("loner") ? new com.najackdo.server.domain.user.entity.QUser(forProperty("loner")) : null;
        this.schedule = inits.isInitialized("schedule") ? new QRentalSchedule(forProperty("schedule")) : null;
    }

}
