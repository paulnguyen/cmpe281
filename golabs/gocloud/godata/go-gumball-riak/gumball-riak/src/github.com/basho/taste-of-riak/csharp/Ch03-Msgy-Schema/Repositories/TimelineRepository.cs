namespace Ch03_Msgy_Schema.Repositories
{
    using Models;
    using RiakClient;

    public class TimelineRepository : Repository<Timeline>
    {
        const string BUCKET_NAME = "Timelines";

        public TimelineRepository(IRiakClient client)
            : base(client)
        {
        }

        protected override string BucketName
        {
            get { return BUCKET_NAME; }
        }
    }
}
