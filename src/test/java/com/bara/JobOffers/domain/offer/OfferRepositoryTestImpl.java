package com.bara.JobOffers.domain.offer;

import com.bara.JobOffers.domain.offer.dto.OfferResponseDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.StreamSupport;


public class OfferRepositoryTestImpl implements OfferRepository {

    Map<String, Offer> database;

    public OfferRepositoryTestImpl() {
        this.database = new ConcurrentHashMap<>();
    }

    public OfferRepositoryTestImpl(Map<String, Offer> database) {
        this.database = new ConcurrentHashMap<>(database);
    }

//    @Override
//    public Offer saveOffer(Offer entity) {
//        if (database.values().stream().anyMatch(offer -> offer.offerUrl().equals(entity.offerUrl()))) {
//            throw new OfferDuplicateException(entity.offerUrl());
//        }
//        UUID id = UUID.randomUUID();
//        Offer offer = Offer.builder()
//                .id(id.toString())
//                .offerUrl(entity.offerUrl())
//                .companyName(entity.companyName())
//                .position(entity.position())
//                .salary(entity.salary())
//                .build();
//        database.put(id.toString(), offer);
//        return offer;
//    }

//    @Override
//    public Optional<Offer> findOfferById(String id) {
//        return Optional.ofNullable(database.get(id));
//    }

//    @Override
//    public List<Offer> saveAll(List<Offer> offers) {
//        return offers.stream()
//                .map(this::saveOffer)
//                .toList();
//    }

    @Override
    public <S extends Offer> S save(S entity) {
        if (database.values().stream().anyMatch(offer -> offer.offerUrl().equals(entity.offerUrl()))) {
            throw new OfferDuplicateException(entity.offerUrl());
        }
        UUID id = UUID.randomUUID();
        Offer offer = new Offer(
                id.toString(),
                entity.companyName(),
                entity.position(),
                entity.salary(),
                entity.offerUrl()
        );
        database.put(id.toString(), offer);
        return (S) offer;
    }

    @Override
    public <S extends Offer> List<S> saveAll(Iterable<S> entities) {
        return StreamSupport.stream(entities.spliterator(), false)
                .map(this::save)
                .toList();
    }


    @Override
    public Optional<Offer> findById(String id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<Offer> findAll() {
        return database.values().stream().toList();
    }

    @Override
    public List<Offer> findAllById(Iterable<String> strings) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(Offer entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Offer> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public boolean existsByOfferUrl(String offerUrl) {
        long count = database.values()
                .stream()
                .filter(offer -> offer.offerUrl().equals(offerUrl))
                .count();
        return count == 1;
    }

    @Override
    public Optional<Offer> findByOfferUrl(String offerUrl) {
        return Optional.of(database.get(offerUrl));
    }

    @Override
    public <S extends Offer> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends Offer> List<S> insert(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public <S extends Offer> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Offer> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Offer> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Offer> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Offer> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Offer> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Offer, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<Offer> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Offer> findAll(Pageable pageable) {
        return null;
    }
}
