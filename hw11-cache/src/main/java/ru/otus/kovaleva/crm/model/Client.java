package ru.otus.kovaleva.crm.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "client")
public final class Client implements Cloneable {

    @Id
    @SequenceGenerator(name = "client_gen", sequenceName = "client_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    private List<Phone> phones;

    public Client(String name) {
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones != null ? new ArrayList<>(phones) : new ArrayList<>();
        initializeRelationships();
    }

    private void initializeRelationships() {
        if (this.phones != null) {
            this.phones.forEach(phone -> phone.setClient(this));
        }
    }

    @Override
    @SuppressWarnings({"java:S2975", "java:S1182"})
    public Client clone() {
        Client clientClone = new Client(
                this.id,
                this.name,
                this.address != null ? new Address(this.address.getId(), this.address.getStreet()) : null,
                new ArrayList<>()
        );

        List<Phone> phoneList = new ArrayList<>();
        for (Phone phone : this.phones) {
            phoneList.add(new Phone(phone.getId(), phone.getNumber(), clientClone));
        }
        clientClone.setPhones(phoneList);
        return clientClone;
    }
}
