package laborator.repository;



import laborator.model.Agent;

public interface IAgentRepository {
    public Agent findByUser(String user);
}
