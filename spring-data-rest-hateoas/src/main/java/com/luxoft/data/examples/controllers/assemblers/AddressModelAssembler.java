package com.luxoft.data.examples.controllers.assemblers;

import com.luxoft.data.examples.controllers.HateoasAddressController;
import com.luxoft.data.examples.controllers.HateoasPersonController;
import com.luxoft.data.examples.model.Address;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AddressModelAssembler implements RepresentationModelAssembler<Address, EntityModel<Address>> {

    @Override
    public EntityModel<Address> toModel(Address address) {
        return EntityModel.of(address,
                linkTo(methodOn(HateoasAddressController.class).findById(address.getId())).withSelfRel(),
                linkTo(methodOn(HateoasAddressController.class).findAll()).withRel("addresses")
        );
    }

    @Override
    public CollectionModel<EntityModel<Address>> toCollectionModel(Iterable<? extends Address> addresses) {

            List<EntityModel<Address>> result =
                    StreamSupport.stream(addresses.spliterator(), false)
                        .map(this::toModel)
                        .collect(Collectors.toList());

        return CollectionModel.of(result,
                linkTo(methodOn(HateoasAddressController.class).findAll()).withSelfRel());

    }
}
