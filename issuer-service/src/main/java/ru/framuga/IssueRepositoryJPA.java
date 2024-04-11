package ru.framuga;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IssueRepositoryJPA extends JpaRepository<Issue, UUID> {

        Issue findIssueById(UUID id);

        void deleteIssueById(UUID id);

}
