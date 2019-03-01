package io.thorntail.examples.book.store.rs.param.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@XmlType(
        name = "NumberedPage",
        propOrder = {"entities", "pageNumber", "defaultPageSize", "totalEntityCount", "totalPageCount", "links"}
)
@JsonPropertyOrder({"entities", "pageNumber", "defaultPageSize", "totalEntityCount", "totalPageCount", "links"})
@XmlAccessorType(XmlAccessType.FIELD)
@JsonAutoDetect(
        fieldVisibility = Visibility.ANY,
        creatorVisibility = Visibility.ANY,
        getterVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE,
        setterVisibility = Visibility.NONE
)
@XmlRootElement(
        name = "page"
)
@Links({@Link(
        rel = "first",
        condition = "${this.pageNumber > 1}",
        header = true,
        bindings = {@Binding(
                name = "page",
                value = "1",
                type = Type.QUERY_PARAM
        ), @Binding(
                name = "perPage",
                value = "${this.defaultPageSize}",
                type = Type.QUERY_PARAM
        )}
), @Link(
        rel = "prev",
        condition = "${this.pageNumber > 1}",
        header = true,
        bindings = {@Binding(
                name = "page",
                value = "${this.pageNumber - 1}",
                type = Type.QUERY_PARAM
        ), @Binding(
                name = "perPage",
                value = "${this.defaultPageSize}",
                type = Type.QUERY_PARAM
        )}
), @Link(
        rel = "self",
        condition = "${this.pageNumber > 0}",
        header = true
), @Link(
        rel = "next",
        condition = "${this.pageNumber != this.totalPageCount}",
        header = true,
        bindings = {@Binding(
                name = "page",
                value = "${this.pageNumber + 1}",
                type = Type.QUERY_PARAM
        ), @Binding(
                name = "perPage",
                value = "${this.defaultPageSize}",
                type = Type.QUERY_PARAM
        )}
), @Link(
        rel = "last",
        condition = "${this.pageNumber != this.totalPageCount}",
        header = true,
        bindings = {@Binding(
                name = "page",
                value = "${this.totalPageCount}",
                type = Type.QUERY_PARAM
        ), @Binding(
                name = "perPage",
                value = "${this.defaultPageSize}",
                type = Type.QUERY_PARAM
        )}
)})
public class NumberedPage<E> implements Page<E> {
    static final String ENTITIES_PROPERTY = "entities";
    static final String PAGE_NUMBER_PROPERTY = "pageNumber";
    static final String DEFAULT_PAGE_SIZE_PROPERTY = "defaultPageSize";
    static final String TOTAL_ENTITY_COUNT_PROPERTY = "totalEntityCount";
    static final String TOTAL_PAGE_COUNT_PROPERTY = "totalPageCount";
    static final String LINKS_PROPERTY = "links";
    static final String LINK_PROPERTY = "link";
    public static final String PAGE_QUERY_PARAM = "page";
    public static final String PER_PAGE_QUERY_PARAM = "perPage";
    @HateoasEntityWrapper
    @XmlElementWrapper(
            name = "entities",
            required = true
    )
    @XmlAnyElement(
            lax = true
    )
    private Collection<E> entities;
    @XmlElement(
            name = "pageNumber",
            required = true,
            nillable = false
    )
    private int pageNumber;
    @XmlElement(
            name = "defaultPageSize",
            required = true,
            nillable = false
    )
    private int defaultPageSize;
    @XmlElement(
            name = "totalEntityCount",
            required = true,
            nillable = false
    )
    private int totalEntityCount;
    @XmlElement(
            name = "totalPageCount",
            required = true,
            nillable = false
    )
    private int totalPageCount;
    @XmlElementWrapper(
            name = "links",
            required = true,
            nillable = false
    )
    @XmlElement(
            name = "link",
            required = true,
            nillable = false
    )
    @XmlJavaTypeAdapter(DelegatedJaxbLinkAdapter.class)
    @JsonSerialize(
            contentUsing = JsonLinkSerializer.class
    )
    @JsonDeserialize(
            contentUsing = JsonLinkDeserializer.class
    )
    private final List<javax.ws.rs.core.Link> links = new ArrayList();

    protected NumberedPage() {
    }

    public Collection<E> getEntities() {
        return (Collection)(this.entities == null ? Collections.emptyList() : this.entities);
    }

    public NumberedPage<E> sort(Comparator<E> entityComparator) {
        if (this.entities != null && this.entities.size() > 1) {
            List<E> sortedEntities = new ArrayList(this.entities);
            Collections.sort(sortedEntities, entityComparator);
            this.entities(sortedEntities);
        }

        return this;
    }

    protected NumberedPage<E> entities(Collection<E> entities) {
        this.entities = entities;
        return this;
    }

    public int getPageNumber() {
        return this.pageNumber;
    }

    protected NumberedPage<E> pageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public int getDefaultPageSize() {
        return this.defaultPageSize;
    }

    protected NumberedPage<E> defaultPageSize(int defaultPageSize) {
        this.defaultPageSize = defaultPageSize;
        return this;
    }

    public int getTotalEntityCount() {
        return this.totalEntityCount;
    }

    protected NumberedPage<E> totalEntityCount(int totalEntityCount) {
        this.totalEntityCount = totalEntityCount;
        return this;
    }

    public int getTotalPageCount() {
        return this.totalPageCount;
    }

    protected NumberedPage<E> totalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
        return this;
    }

    public Collection<javax.ws.rs.core.Link> getLinks() {
        return Collections.unmodifiableList(this.links);
    }

    public NumberedPage<E> addLinks(javax.ws.rs.core.Link... links) {
        if (links != null && links.length != 0) {
            Collections.addAll(this.links, links);
        }

        return this;
    }

    public Collection<javax.ws.rs.core.Link> getLinks(String rel) {
        if (this.links.isEmpty()) {
            return Collections.emptyList();
        } else {
            List<javax.ws.rs.core.Link> links = new ArrayList();
            Iterator i$ = this.links.iterator();

            while(i$.hasNext()) {
                javax.ws.rs.core.Link link = (javax.ws.rs.core.Link)i$.next();
                if (StringUtils.equals(rel, link.getRel())) {
                    links.add(link);
                }
            }

            return links.isEmpty() ? Collections.emptyList() : Collections.unmodifiableList(links);
        }
    }

    public javax.ws.rs.core.Link getLink(String rel) {
        Collection<javax.ws.rs.core.Link> links = this.getLinks(rel);
        return !links.isEmpty() ? (javax.ws.rs.core.Link)links.iterator().next() : null;
    }

    public <R> R apply(Function<? super NumberedPage<E>, R> function) {
        return ((Function)Objects.requireNonNull(function)).apply(this);
    }

    public void accept(Consumer<? super NumberedPage<E>> consumer) {
        ((Consumer)Objects.requireNonNull(consumer)).accept(this);
    }

    public static <E> NumberedPage<E> fromEmptyData(int pageSize) {
        return fromPartialData(Collections.emptyList(), 0, 1, pageSize);
    }

    public static <T, E> NumberedPage<E> fromWholeData(Collection<T> dataObjects, EntityAdapter<T, E> entityAdapter, int pageNumber, int pageSize) {
        Collection<T> dataObjectsSubCollection = buildSubCollection(dataObjects, pageNumber, pageSize);
        return fromPartialData(dataObjectsSubCollection, entityAdapter, dataObjects.size(), pageNumber, pageSize);
    }

    public static <T, E> NumberedPage<E> fromPartialData(Collection<T> dataObjects, EntityAdapter<T, E> entityAdapter, int totalEntityCount, int pageNumber, int defaultPageSize) {
        List<E> entities = new ArrayList(dataObjects.size());
        Iterator i$ = dataObjects.iterator();

        while(i$.hasNext()) {
            T dataObject = i$.next();
            entities.add(entityAdapter.adapt(dataObject));
        }

        return fromPartialData(entities, totalEntityCount, pageNumber, defaultPageSize);
    }

    public static <E> NumberedPage<E> fromWholeData(Collection<E> entities, int pageNumber, int pageSize) {
        Collection<E> entitiesSubCollection = buildSubCollection(entities, pageNumber, pageSize);
        return fromPartialData(entitiesSubCollection, entities.size(), pageNumber, pageSize);
    }

    public static <E> NumberedPage<E> fromPartialData(Collection<E> entities, int totalEntityCount, int pageNumber, int defaultPageSize) {
        int entityCount = entities.size();
        if (pageNumber <= 0) {
            throw new IllegalArgumentException("Page number must be greater than 0");
        } else if (defaultPageSize <= 0) {
            throw new IllegalArgumentException("Default page size must be greater than 0");
        } else if (totalEntityCount < 0) {
            throw new IllegalArgumentException("Total entity count must be greater than or equals to 0");
        } else if (entityCount > defaultPageSize) {
            throw new IllegalArgumentException("Entity count must be less or equal to default page size");
        } else if (entityCount > totalEntityCount) {
            throw new IllegalArgumentException("Entity count must be less or equal to total entity count");
        } else if (entityCount == 0 && totalEntityCount != 0) {
            throw new NotFoundException();
        } else {
            int realPageNumber = pageNumber == 1 && totalEntityCount == 0 ? 0 : pageNumber;
            int totalPageCount = (int)Math.ceil((double)totalEntityCount / (double)defaultPageSize);
            if (realPageNumber > totalPageCount) {
                throw new NotFoundException();
            } else if (realPageNumber != totalPageCount && entityCount != defaultPageSize) {
                throw new IllegalArgumentException("Entity count must be equal to default page size");
            } else {
                return (new NumberedPage()).entities(entities).defaultPageSize(defaultPageSize).pageNumber(realPageNumber).totalEntityCount(totalEntityCount).totalPageCount(totalPageCount);
            }
        }
    }

    public static int getPageNumber(MultivaluedMap<String, String> queryParams) {
        String pageQueryParam = (String)queryParams.getFirst("page");
        if (pageQueryParam == null) {
            return 1;
        } else {
            try {
                int pageNumber = Integer.parseInt(pageQueryParam);
                return pageNumber <= 0 ? 1 : pageNumber;
            } catch (NumberFormatException var3) {
                return 1;
            }
        }
    }

    public static int getPageSize(MultivaluedMap<String, String> queryParams, int defaultValue, int maxValue) {
        String perPageQueryParam = (String)queryParams.getFirst("perPage");
        if (perPageQueryParam == null) {
            return defaultValue;
        } else {
            try {
                int pageSize = Integer.parseInt(perPageQueryParam);
                return pageSize <= 0 ? defaultValue : Math.min(pageSize, maxValue);
            } catch (NumberFormatException var5) {
                return defaultValue;
            }
        }
    }

    private static <T> Collection<T> buildSubCollection(Collection<T> data, int pageNumber, int pageSize) {
        if (pageNumber <= 0) {
            throw new IllegalArgumentException("Page number must be greater than 0");
        } else if (pageSize <= 0) {
            throw new IllegalArgumentException("Page size must be greater than 0");
        } else if (data.isEmpty()) {
            return Collections.emptyList();
        } else {
            int totalDataCount = data.size();
            int fromIndex = (pageNumber - 1) * pageSize;
            int toIndex = fromIndex + pageSize;
            toIndex = toIndex > totalDataCount ? totalDataCount : toIndex;
            return fromIndex >= 0 && fromIndex <= toIndex ? (new ArrayList(data)).subList(fromIndex, toIndex) : Collections.emptyList();
        }
    }
}

