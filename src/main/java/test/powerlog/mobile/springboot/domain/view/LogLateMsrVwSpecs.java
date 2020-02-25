package test.powerlog.mobile.springboot.domain.view;

import org.springframework.data.jpa.domain.Specification;

public class LogLateMsrVwSpecs {
    public static Specification<LogLateMsrVw> withTitle(String title) {
        return (Specification<LogLateMsrVw>) ((root, query, builder) ->
                builder.equal(root.get("title"), title)
        );
    }
}
