package io.thorntail.examples.book.store.repository;

import io.thorntail.examples.book.store.rs.param.BookParam;
import io.thorntail.examples.book.store.model.Book;
import io.thorntail.examples.book.store.model.Book_;
import io.thorntail.examples.book.store.model.dto.BookDto;
import io.thorntail.examples.book.store.model.enums.SortStrategy;
import io.thorntail.examples.book.store.model.mapper.AuthorMapper;
import io.thorntail.examples.book.store.model.mapper.BookMapper;
import io.thorntail.examples.book.store.repository.utils.SqlPaginator;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;
import java.util.stream.Collectors;

public class BookStoreRepository {
    @PersistenceContext(name = "bookStorePU")
    private EntityManager em;

    @Inject
    private BookMapper bookMapper;

    @Inject
    private AuthorMapper authorMapper;

    private CriteriaBuilder criteriaBuilder;

    @PostConstruct
    private void init() {
        criteriaBuilder = em.getCriteriaBuilder();
    }

    public List<BookDto> findBook(final SqlPaginator sqlPaginator,
                                  final BookParam bookParam,
                                  final Map<String, SortStrategy> sortInstructions){
        Objects.requireNonNull(sqlPaginator);

        final CriteriaQuery<Book> criteria = criteriaBuilder.createQuery(Book.class);
        final Root<Book> listRoot = criteria.from(Book.class);

        final List<Predicate> predicates = buildBookCriteria(listRoot, bookParam);
        final List<Order> orders = buildSortsCriteria(listRoot, sortInstructions);

        criteria.select(listRoot)
                .where(predicates.toArray(new Predicate[0]))
                .orderBy(orders);

        final TypedQuery<Book> query = em.createQuery(criteria);
        final List<Book> resultList = query
                .setFirstResult(sqlPaginator.getBeginIndex())
                .setMaxResults(sqlPaginator.getNbResults())
                .getResultList();

        return bookMapper.bookCollectionToBookDtos(resultList);
    }

    public int countBook(final BookParam book) {
        final CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        final Root<Book> listRoot = criteriaQuery.from(Book.class);

        final List<Predicate> predicates = buildBookCriteria(listRoot, book);

        criteriaQuery.select(criteriaBuilder.count(listRoot));
        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        final TypedQuery<Long> query = em.createQuery(criteriaQuery);

        return query.getSingleResult().intValue();
    }

    private List<Predicate> buildBookCriteria(
            final Root<Book> listRoot,
            final BookParam book) {
        final List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(book)) {
            if (Objects.nonNull(book.getTitle()) && !"".equals(book.getTitle())) {
                final Path<String> titleField = listRoot.get(Book_.title);
                predicates.add(criteriaBuilder.and(criteriaBuilder.like(titleField, book.getTitle())));
            }
          /*  if (book.getPrice()) {
                final Path<String> groupIdField = listRoot.get(Book_.price);
                predicates.add(criteriaBuilder.and(criteriaBuilder.like(groupIdField, book.getPrice())));
            }*/
        }
        return predicates;
    }

    private List<Order> buildSortsCriteria(Root<Book> listRoot, final Map<String, SortStrategy> sortInstructions){
        List<Order> orders= new ArrayList();

        if(Objects.nonNull(sortInstructions)) {
            orders = sortInstructions.entrySet()
                    .stream()
                    .map(entry->sortInstructionToCriteria(listRoot,entry))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
        }
        return orders;
    }

    private Optional<Order> sortInstructionToCriteria(Root<Book> listRoot, final Map.Entry<String, SortStrategy> entry){
        Optional<Order> order;
        switch (entry.getValue()) {
            case asc:
                order = Optional.of(criteriaBuilder.asc(listRoot.get(entry.getKey())));
                break;
            case desc:
                order= Optional.of(criteriaBuilder.desc(listRoot.get(entry.getKey())));
                break;
            default: throw new IllegalArgumentException();
        }
        return order;
    }

}
