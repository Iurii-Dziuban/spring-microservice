package com.iurii.microservice.persistance.entity;

import org.springframework.data.domain.Persistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Entity
@Table(
        schema = "WEBSERVICE",
        name = "USER"
)
public class User implements Persistable<String> {
    @Id
    @Column(
            name = "ID",
            columnDefinition = "char"
    )
    private String id;
    @Column(
            name = "NAME",
            columnDefinition = "char",
            nullable = false
    )
    private String name;
    @Column(
            name = "BIRTH_DATE",
            columnDefinition = "date",
            nullable = false
    )
    private LocalDate birthDate;
    @Column(
            name = "UPDATED_TIME",
            columnDefinition = "timestamp",
            nullable = false
    )
    private ZonedDateTime updatedTime;
    @Column(
            name = "MONEY",
            columnDefinition = "decimal",
            nullable = false
    )
    private long money;
    @Transient
    private boolean isNew = false;

    public User() {
    }

    public void setNew() {
        this.isNew = true;
    }

    public String getId() {
        return this.id;
    }

    public boolean isNew() {
        return this.isNew;
    }

    public static User.UserBuilder builder() {
        return new User.UserBuilder();
    }

    public User(String id, String name, LocalDate birthDate, ZonedDateTime updatedTime, long money, boolean isNew) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.updatedTime = updatedTime;
        this.money = money;
        this.isNew = isNew;
    }

    public String getName() {
        return this.name;
    }

    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    public ZonedDateTime getUpdatedTime() {
        return this.updatedTime;
    }

    public long getMoney() {
        return this.money;
    }

    public boolean equals(Object o) {
        if(o == this) {
            return true;
        } else if(!(o instanceof User)) {
            return false;
        } else {
            User other = (User)o;
            if(!other.canEqual(this)) {
                return false;
            } else {
                String this$id = this.getId();
                String other$id = other.getId();
                if(this$id == null) {
                    if(other$id != null) {
                        return false;
                    }
                } else if(!this$id.equals(other$id)) {
                    return false;
                }

                String this$name = this.getName();
                String other$name = other.getName();
                if(this$name == null) {
                    if(other$name != null) {
                        return false;
                    }
                } else if(!this$name.equals(other$name)) {
                    return false;
                }

                LocalDate this$birthDate = this.getBirthDate();
                LocalDate other$birthDate = other.getBirthDate();
                if(this$birthDate == null) {
                    if(other$birthDate != null) {
                        return false;
                    }
                } else if(!this$birthDate.equals(other$birthDate)) {
                    return false;
                }

                label46: {
                    ZonedDateTime this$updatedTime = this.getUpdatedTime();
                    ZonedDateTime other$updatedTime = other.getUpdatedTime();
                    if(this$updatedTime == null) {
                        if(other$updatedTime == null) {
                            break label46;
                        }
                    } else if(this$updatedTime.equals(other$updatedTime)) {
                        break label46;
                    }

                    return false;
                }

                if(this.getMoney() != other.getMoney()) {
                    return false;
                } else if(this.isNew() != other.isNew()) {
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof User;
    }

    public int hashCode() {
        boolean PRIME = true;
        byte result = 1;
        String $id = this.getId();
        int result1 = result * 59 + ($id == null?43:$id.hashCode());
        String $name = this.getName();
        result1 = result1 * 59 + ($name == null?43:$name.hashCode());
        LocalDate $birthDate = this.getBirthDate();
        result1 = result1 * 59 + ($birthDate == null?43:$birthDate.hashCode());
        ZonedDateTime $updatedTime = this.getUpdatedTime();
        result1 = result1 * 59 + ($updatedTime == null?43:$updatedTime.hashCode());
        long $money = this.getMoney();
        result1 = result1 * 59 + (int)($money >>> 32 ^ $money);
        result1 = result1 * 59 + (this.isNew()?79:97);
        return result1;
    }

    public static class UserBuilder {
        private String id;
        private String name;
        private LocalDate birthDate;
        private ZonedDateTime updatedTime;
        private long money;
        private boolean isNew;

        UserBuilder() {
        }

        public User.UserBuilder id(String id) {
            this.id = id;
            return this;
        }

        public User.UserBuilder name(String name) {
            this.name = name;
            return this;
        }

        public User.UserBuilder birthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public User.UserBuilder updatedTime(ZonedDateTime updatedTime) {
            this.updatedTime = updatedTime;
            return this;
        }

        public User.UserBuilder money(long money) {
            this.money = money;
            return this;
        }

        public User.UserBuilder isNew(boolean isNew) {
            this.isNew = isNew;
            return this;
        }

        public User build() {
            return new User(this.id, this.name, this.birthDate, this.updatedTime, this.money, this.isNew);
        }

        public String toString() {
            return "User.UserBuilder(id=" + this.id + ", name=" + this.name + ", birthDate="
                    + this.birthDate + ", updatedTime=" + this.updatedTime + ", money="
                    + this.money + ", isNew=" + this.isNew + ")";
        }
    }
}
