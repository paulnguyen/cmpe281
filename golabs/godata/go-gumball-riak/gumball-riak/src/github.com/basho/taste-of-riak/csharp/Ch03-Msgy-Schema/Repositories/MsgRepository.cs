namespace Ch03_Msgy_Schema.Repositories
{
    using Models;
    using RiakClient;

    public class MsgRepository : Repository<Msg>
    {
        const string BUCKET_NAME = "Msgs";

        public MsgRepository(IRiakClient client)
            : base(client)
        {
        }

        protected override string BucketName
        {
            get { return BUCKET_NAME; }
        }
    }
}
