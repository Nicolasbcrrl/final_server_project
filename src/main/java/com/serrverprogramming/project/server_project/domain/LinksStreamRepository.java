package com.serrverprogramming.project.server_project.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LinksStreamRepository extends CrudRepository<LinksStream, Long> {
    List<LinksStream> findByLink(String link);
    List<LinksStream> findLinksStreamByMovie_Id(Long id);
}
