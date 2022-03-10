package com.educandoweb.cursomc.services;

import com.educandoweb.cursomc.domain.Address;
import com.educandoweb.cursomc.domain.City;
import com.educandoweb.cursomc.domain.Client;
import com.educandoweb.cursomc.domain.enums.ClientType;
import com.educandoweb.cursomc.domain.enums.Profile;
import com.educandoweb.cursomc.dto.ClientDTO;
import com.educandoweb.cursomc.dto.ClientNewDTO;
import com.educandoweb.cursomc.repositories.AddressRepository;
import com.educandoweb.cursomc.repositories.ClientRepository;
import com.educandoweb.cursomc.security.UserSS;
import com.educandoweb.cursomc.services.exceptions.AuthorizationException;
import com.educandoweb.cursomc.services.exceptions.DataIntegrityException;
import com.educandoweb.cursomc.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private BCryptPasswordEncoder pe;

    @Autowired
    private ClientRepository repo;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private ImageService imageService;

    @Value("${img.prefix.client.profile}")
    private String prefix;

    @Value("${img.profile.size}")
    private Integer size;

    public Client search(Integer id){

        UserSS user = UserService.authenticated();
        if (user == null || !user.hasRole(Profile.ADMIN) && !id.equals(user.getId())){
            throw new AuthorizationException("Access Denied");
        }

        Optional<Client> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Object not found! Id: " + id + ", Type: " + Client.class.getName()));
    }

    @Transactional
    public Client insert(Client obj){
        obj.setId(null);
        obj = repo.save(obj);
        addressRepository.saveAll(obj.getAddresses());
        return obj;
    }

    public Client update(Client obj){
        Client newObj = search(obj.getId());
        updateData(newObj, obj);
        return repo.save(newObj);
    }

    public void delete(Integer id){
        search(id);
        try{
            repo.deleteById(id);
        }
        catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("It's not possible to delete a client because there are related entities!");
        }
    }

    public List<Client> findAll(){
        return repo.findAll();
    }

    public Client findByEmail(String email){
        UserSS user = UserService.authenticated();
        if (user == null || !user.hasRole(Profile.ADMIN) && !email.equals((user.getUsername()))){
            throw new AuthorizationException("Access Denied");
        }
        Client obj = repo.findByEmail(email);
        if (obj == null){
            throw new ObjectNotFoundException("Object not found! Id: " + user.getId() + ", Tipo: " + Client.class.getName());
        }
        return obj;
    }

    public Page<Client> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    public Client fromDTO(ClientDTO objDto){
        return new Client(objDto.getId(), objDto.getName(), objDto.getEmail(), null, null, null);
    }

    public Client fromDTO(ClientNewDTO objDto){
        Client cli = new Client(null, objDto.getName(), objDto.getEmail(), objDto.getSsnOrEin(), ClientType.toEnum(objDto.getType()), pe.encode(objDto.getPassword()));
        City city = new City(objDto.getCityId(), null, null);
        Address ad = new Address(null, objDto.getStreetAddress(), objDto.getNumber(), objDto.getAddressLine(),
                objDto.getRegion(), objDto.getPostalCode(), cli, city);
        cli.getAddresses().add(ad);
        cli.getPhoneNumbers().add(objDto.getPhoneNumber1());
        if (objDto.getPhoneNumber2() != null){
            cli.getPhoneNumbers().add(objDto.getPhoneNumber2());
        }
        if (objDto.getPhoneNumber3() != null){
            cli.getPhoneNumbers().add(objDto.getPhoneNumber3());
        }
        return cli;
    }

    private void updateData(Client newObj, Client obj){
        newObj.setName(obj.getName());
        newObj.setEmail(obj.getEmail());
    }

    public URI uploadProfilePicture(MultipartFile multipartFile){

        UserSS user = UserService.authenticated();
        if (user == null){
            throw new AuthorizationException("Access Denied");
        }

        BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
        jpgImage = imageService.cropSquare(jpgImage);
        jpgImage = imageService.resize(jpgImage, size);

        String fileName = prefix + user.getId() + ".jpg";

        return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
    }
}
