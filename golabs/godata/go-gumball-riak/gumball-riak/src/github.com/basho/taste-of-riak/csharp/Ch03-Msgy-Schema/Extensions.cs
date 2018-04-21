namespace Ch03_Msgy_Schema
{
    using System;

    public static class Extensions
    {
        public static string AsISO8601(this DateTime dateTime)
        {
            return dateTime.ToString("s");
        }
    }
}
