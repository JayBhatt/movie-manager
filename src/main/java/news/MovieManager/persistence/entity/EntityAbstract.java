package news.MovieManager.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.MappedSuperclass;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Getter
@Setter
@MappedSuperclass
public abstract class EntityAbstract {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityAbstract.class);

    private static final String[] SENSITIVE_FIELDS = new String[] {
            "password",
            "username",
            "email",
            "secret",
            "api_key",
            "api_secret"
    };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    @Column(name = "updated_on", insertable = false)
    private LocalDateTime updatedOn;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean active = true;

    @PrePersist
    public void prePersist() {
        this.createdOn = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedOn = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof EntityAbstract)) {
            return false;
        }
        EntityAbstract entity = (EntityAbstract) o;
        return (this == o || getId() != null && Objects.equals(getId(), entity.getId()));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    /**
     * Hides sensitive information when converting entities to string and protects it from accidental logging.
     *
     * @return
     */
    @Override
    public String toString() {
        ReflectionToStringBuilder stringBuilder = new ReflectionToStringBuilder(
                this, ToStringStyle.MULTI_LINE_STYLE) {
            @Override
            protected boolean accept(Field field) {
                return (
                        !Arrays.asList(SENSITIVE_FIELDS).contains(field.getName().toLowerCase())
                );
            }
        };
        return stringBuilder.toString();
    }
}