import List.LinkedList;

import java.util.*;

public class EntityTariff implements Tariff, Cloneable {
    private LinkedList<Service> services;

    public EntityTariff() {
        services = new LinkedList<Service>();
    }

    @Override
    public boolean add(Service service) {
        Objects.requireNonNull(service);
        try {
            services.add(service);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean add(Service service, int index) {
        Objects.requireNonNull(service);
        if (index < 0 || index >= services.getSize()) throw new IndexOutOfBoundsException();
        try {
            services.add(service, index);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Service get(int index) {
        if (index < 0 || index >= services.getSize()) throw new IndexOutOfBoundsException();
        return services.get(index);
    }

    @Override
    public Service get(String title) {
        Objects.requireNonNull(title);
        for (int i = 0; i < services.getSize(); i++) {
            if (services.get(i).getTitle().equals(title)) return services.get(i);
        }
        throw new NoSuchElementException();
    }

    @Override
    public boolean isContain(String title) {
        Objects.requireNonNull(title);
        for (int i = 0; i < services.getSize(); i++) {
            if (services.get(i).getTitle().equals(title)) return true;
        }
        return false;
    }

    @Override
    public Service set(int index, Service service) {
        Objects.requireNonNull(service);
        if (index < 0 || index >= services.getSize()) throw new IndexOutOfBoundsException();
        Service forReturn = services.get(index);
        services.set(service, index);
        return forReturn;
    }

    @Override
    public Service delete(int index) {
        if (index < 0 || index >= services.getSize()) throw new IndexOutOfBoundsException();
        Service forReturn = services.get(index);
        services.delete(index);
        return forReturn;
    }

    @Override
    public int getSize() {
        return services.getSize();
    }

    @Override
    public Service[] getServices() {
        Service[] services = new Service[this.services.getSize()];
        for (int i = 0; i < services.length; i++) {
            services[i] = this.services.get(i);
        }
        return services;
    }

    @Override
    public Service[] SortedServicesByCost() {
        Service[] newBufServices = getServices();
        Arrays.sort(newBufServices);
        return newBufServices;
    }

    @Override
    public double cost() {
        double cost = 50;
        Service[] newService = getServices();
        for (Service service : this) {
            cost += service.getCost();
        }
        return cost;
    }

    public Service[] getTypedServices(ServiceTypes serviceType) {
        Objects.requireNonNull(serviceType);
        ArrayList<Service> list = new ArrayList<Service>();
        for (int i = 0; i < services.getSize(); i++) {
            if (services.get(i) != null && services.get(i).getServiceTypes().equals(serviceType)) {
                list.add(services.get(i));
            }
        }
        return (Service[]) list.toArray();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("services: \n");
        for (Service service : this) {
            sb.append(services.toString()).append('\n');
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        EntityTariff that = (EntityTariff) obj;
        boolean answer = that.getSize() == this.getSize();
        if (answer) {
            for (int i = 0; i < getSize(); i++) {
                answer &= (that.getServices()[i].equals(this.getServices()[i]));
            }
        }
        return answer;
    }

    @Override
    public int hashCode() {
        int hash = 71;
        for(Service service:this){
            hash *= service.hashCode();
        }
        return hash;
    }

    @Override
    public Tariff clone() throws CloneNotSupportedException {
        return (Tariff) super.clone();
    }

    @Override
    public boolean delete(Service service) {
        Objects.requireNonNull(service);
        for (int i = 0; i < services.getSize(); i++) {
            if (services.get(i).equals(service)) {
                services.delete(i);
                return true;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public int indexOf(Service service) {
        Objects.requireNonNull(service);
        return services.indexOf(service);
    }

    @Override
    public int lastIndexOf(Service service) {
        Objects.requireNonNull(service);
        return services.lastIndexOf(service);
    }

    @Override
    public Iterator<Service> iterator() {
        return new ServiceIterator(services);
    }

    private class ServiceIterator implements Iterator<Service> {
        private LinkedList<Service> services;

        public ServiceIterator(LinkedList<Service> services) {
            this.services = services;
        }

        @Override
        public boolean hasNext() {
            return services.get(0) != null;
        }

        @Override
        public Service next() {
            Service buf = services.get(0);
            remove();
            return buf;
        }

        @Override
        public void remove() {
            services.delete(0);
        }
    }
}
