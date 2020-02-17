package test.powerlog.mobile.springboot.domain.products;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LogLateMsrVwSpecs {
    public static Specification<LogLateMsrVw> withTitle(String title) {
        return (Specification<LogLateMsrVw>) ((root, query, builder) ->
                builder.equal(root.get("title"), title)
        );
    }
}
