namespace Ch03_Msgy_Schema.Repositories
{
    using Models;
    using RiakClient;

    public class UserRepository : Repository<User>
    {
        const string BUCKET_NAME = "Users";

        public UserRepository(IRiakClient client)
            : base(client)
        {
        }

        protected override string BucketName
        {
            get { return BUCKET_NAME; }
        }
    }
}
